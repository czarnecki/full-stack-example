const String getTimeline = r'''
query GetTimeLine($userId: Int!) {
  action: getTimelineFromUser(input: {userId: $userId}) {
    timeline {
      post {
        id
        message
        creationDate
      }
      user {
        id
        username
        firstName
        lastName
      }
    }
    user {
      id
      username
      firstName
      lastName
    }
  }
}
''';
