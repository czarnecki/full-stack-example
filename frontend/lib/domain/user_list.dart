import 'user.dart';

class UserList {
  static List<UserListItem> fromQuery(dynamic data) {
    List userListData = data['action']['users'];
    var users = userListData.map(UserListItem.fromQuery).toList();
    return users;
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
