const String sendPost = r'''
mutation SendPost($message: String!) {
  action: postMessage(input: {message: $message}) {
    post {
      id
    }
  }
}
''';