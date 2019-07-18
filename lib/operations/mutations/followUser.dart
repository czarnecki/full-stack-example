const String followUser = r'''
mutation FollowUser($userId: Int!, $otherUserId: Int!) {
  followUser(input: {followingUserId: $userId, followedUserId: $otherUserId} ) {
    following {
      id
    }
  }
}
''';