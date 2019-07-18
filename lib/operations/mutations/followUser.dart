const String followUser = r'''
mutation FollowUser($userId: Int!, $otherUserId: Int!) {
  action: followUser(input: {followingUserId: $userId, followedUserId: $otherUserId}) {
    following {
      id
    }
  }
}
''';