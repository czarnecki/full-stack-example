const String getTimeline = r'''
query GetTimeLine {
  action: getTimelineFromUser {
    timeline {
      post {
        id
        message
        creationDate
      }
      user {
        username
        givenName
        familyName
      }
    }
    user {
      username
      givenName
      familyName
    }
  }
}
''';
