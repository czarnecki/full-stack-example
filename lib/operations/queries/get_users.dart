const String getUsers = r'''
query GetUsers {
  action: getUserListWithFollowStatus {
    users: otherUsersWithFollowStatus {
      user {
        username
        givenName
        familyName
      }
      isFollowing
    }
  }
}
''';
