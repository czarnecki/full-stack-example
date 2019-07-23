const String follow = r'''
mutation FollowUser($followedUserUsername: String!) {
  action: followUser(input: {followedUserUsername: $followedUserUsername}) {
    following {
      username
    }
  }
}
''';