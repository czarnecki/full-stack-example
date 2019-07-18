const String unfollowUser = r'''
mutation UnfollowUser($userId: Int!, $otherUserId: Int!) {
  action: unfollowUser(input: {followingUserId: $userId, followedUserId: $otherUserId} ) {
    following {
      id
    }
  }
}
''';