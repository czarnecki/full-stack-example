const String getTimelineWithCurrentUser = r'''
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
  currentUser: getCurrentlyLoggedInUser {
    user {
      username
    }
  }
}
''';
