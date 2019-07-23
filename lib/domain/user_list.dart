import 'package:graphql_flutter/graphql_flutter.dart';

import 'user.dart';

class UserList {
  final List<UserListItem> users;

  UserList(this.users);

  static UserList fromQuery(QueryResult result) {
    List userListData = result.data['action']['users'];
    var users = userListData.map(UserListItem.fromQuery).toList();
    return UserList(users);
  }
}

class UserListItem {
  final User user;
  final bool isFollowing;

  UserListItem._(this.user, this.isFollowing);

  static UserListItem fromQuery(dynamic data) {
    var user = User.fromQuery(data);
    var isFollowing = data['isFollowing'];
    return UserListItem._(user, isFollowing);
  }
}
