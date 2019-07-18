const String getUsers = r'''
query GetUsers($userId: Int!) {
  getUserListWithFollowStatus(input: {userId: $userId}) {
    otherUsersWithFollowStatus {
      user {
        id
        username
        firstName
        lastName
      }
      isFollowing
    }
  }
}
''';
