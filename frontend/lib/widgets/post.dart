import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:graphql_flutter/graphql_flutter.dart';

import '../operations/mutations/mutations.dart' as mutation;

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
                  : Text('Post send sucesfully'),
              behavior: SnackBarBehavior.floating,
              action: SnackBarAction(
                label: 'Close',
                onPressed: () => Scaffold.of(context).removeCurrentSnackBar(),
              ),
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
        alignment: FractionalOffset.bottomCenter,
        margin: EdgeInsets.all(8.0),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Flexible(
              child: TextField(
                autofocus: true,
                maxLines: null,
                keyboardType: TextInputType.multiline,
                controller: _textController,
                decoration: InputDecoration(
                  hintText: 'Message',
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(32.0),
                  ),
                  fillColor: Colors.white,
                  filled: true,
                ),
              ),
            ),
            _SendPost(_textController),
          ],
        ),
      ),
    );
  }
}

class _SendPost extends StatelessWidget {
  final TextEditingController _textController;

  _SendPost(this._textController);

  _sendPost(RunMutation sendPost, BuildContext context) {
    if (_textController.text.length > 0) {
      sendPost({
        'message': _textController.text,
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Mutation(
      builder: (RunMutation sendPost, QueryResult result) {
        return RawMaterialButton(
          onPressed: () => _sendPost(sendPost, context),
          child: Icon(
            Icons.send,
            color: Colors.white,
          ),
          materialTapTargetSize: MaterialTapTargetSize.padded,
          shape: CircleBorder(),
          fillColor: Colors.blue,
          constraints: BoxConstraints(),
          elevation: 0,
          padding: EdgeInsets.all(15),
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
