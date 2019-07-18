import 'package:flutter/material.dart';
import 'package:frontend/operations/mutations/mutations.dart' as mutation;
import 'package:graphql_flutter/graphql_flutter.dart';

class Post extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return FloatingActionButton(
      tooltip: 'Write post',
      onPressed: () => Navigator.push(
        context,
        MaterialPageRoute<void>(
          builder: (context) => _TextWidget(),
        ),
      ),
      child: Icon(Icons.add),
    );
  }
}

class _TextWidget extends StatelessWidget {
  final TextEditingController _textController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('Write new post'),
        ),
        body: TextField(
          autofocus: true,
          keyboardType: TextInputType.multiline,
          maxLines: null,
          controller: _textController,
          decoration: InputDecoration(
            border: OutlineInputBorder(),
          ),
        ),
        floatingActionButton: _SendPost(_textController));
  }
}

class _SendPost extends StatelessWidget {
  final TextEditingController _controller;

  _SendPost(this._controller);

  @override
  Widget build(BuildContext context) {
    return Mutation(
      builder: (RunMutation sendPost, QueryResult result) {
        return FloatingActionButton(
          onPressed: () {
            if (_controller.text.length > 0) {
              sendPost({
                'userId': 20,
                'message': _controller.text,
              });
            }
          },
          child:
              result.loading ? CircularProgressIndicator() : Icon(Icons.send),
        );
      },
      onCompleted: (dynamic resultData) {
        _controller.clear();
        Navigator.pop(context);
      },
      options: MutationOptions(
        document: mutation.sendPost,
      ),
    );
  }
}
