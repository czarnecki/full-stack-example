const String sendPost = r'''
mutation SendPost($userId: Int!, $message: String!) {
  action: postMessage(input: {userId: $userId, message: $message}) {
    post {
      id
    }
  }
}
''';