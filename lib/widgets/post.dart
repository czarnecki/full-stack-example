import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:frontend/operations/mutations/mutations.dart' as mutation;
import 'package:graphql_flutter/graphql_flutter.dart';

class Post extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return FloatingActionButton(
      tooltip: 'Write post',
      onPressed: () async {
        var error = await Navigator.of(context).push(
          PageRouteBuilder(
            opaque: false,
            pageBuilder: (BuildContext context, _, __) {
              return BackdropFilter(
                filter: ImageFilter.blur(sigmaX: 5, sigmaY: 5),
                child: _TextWidget(),
              );
            },
          ),
        );
        if (error != null) {
          Scaffold.of(context).showSnackBar(
            SnackBar(
              content: error
                  ? Text('There was an error sending your post')
                  : Text('Message send sucesfully'),
              behavior: SnackBarBehavior.floating,
              action: SnackBarAction(
                  label: 'Close',
                  onPressed: () =>
                      Scaffold.of(context).removeCurrentSnackBar()),
            ),
          );
        }
      },
      child: Icon(Icons.add),
    );
  }
}

class _TextWidget extends StatelessWidget {
  final TextEditingController _textController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.transparent,
      appBar: AppBar(
        title: Text('Write new post'),
      ),
      body: Container(
        padding: EdgeInsets.all(5.0),
        alignment: FractionalOffset.bottomCenter,
        child: TextField(
          autofocus: true,
          maxLength: 140,
          onSubmitted: (message) => print(message),
          maxLines: null,
          keyboardType: TextInputType.multiline,
          controller: _textController,
          decoration: InputDecoration(
            hintText: 'Message',
            border: OutlineInputBorder(),
            fillColor: Colors.white,
            suffixIcon: _SendPost(_textController),
            filled: true,
          ),
        ),
      ),
    );
  }
}

class _SendPost extends StatelessWidget {
  final TextEditingController _textController;

  _SendPost(this._textController);

  _sendPost(RunMutation sendPost) {
    sendPost({'message': _textController.text});
  }

  @override
  Widget build(BuildContext context) {
    return Mutation(
      builder: (RunMutation sendPost, QueryResult result) {
        return IconButton(
          onPressed: () => _sendPost(sendPost),
          icon: result.loading ? CircularProgressIndicator() : Icon(Icons.send),
        );
      },
      update: (Cache cache, QueryResult result) {
        _textController.clear();
        Navigator.pop(context, result.hasErrors);
      },
      options: MutationOptions(
        document: mutation.sendPost,
      ),
    );
  }
}
