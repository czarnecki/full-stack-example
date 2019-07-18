const String getUsers = r'''
query GetUsers($userId: Int!) {
  action: getUserListWithFollowStatus(input: {userId: $userId}) {
    users: otherUsersWithFollowStatus {
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
