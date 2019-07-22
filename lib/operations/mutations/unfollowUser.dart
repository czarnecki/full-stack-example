const String unfollow = r'''
mutation UnfollowUser($followedUserUsername: String!) {
  action: unfollowUser(input: {followedUserUsername: $followedUserUsername} ) {
    following {
      username
    }
  }
}
''';