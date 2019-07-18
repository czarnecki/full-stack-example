import 'package:flutter/material.dart';
import 'package:frontend/operations/mutations/mutations.dart' as mutation;
import 'package:frontend/operations/queries/queries.dart' as query;
import 'package:graphql_flutter/graphql_flutter.dart';

class UserList extends StatelessWidget {
  static final _userId = 20;

  @override
  Widget build(BuildContext context) {
    return Query(
      options: QueryOptions(document: query.getUsers,
          variables: {'userId': _userId}),
      builder: (QueryResult result, {BoolCallback refetch}) {
        if (result.hasErrors) {
          return Text(result.errors.toString());
        }
        if (result.loading) {
          return Center(
            child: CircularProgressIndicator(),
          );
        }
        List users = result.data['action']['users'];
        return ListView.builder(
          itemCount: users.length,
          itemBuilder: (context, index) {
            return _Follow(_userId, users[index]);
          },
        );
      },
    );
  }
}

class _Follow extends StatelessWidget {
  final int _userId;
  final Map<String, dynamic> _userItem;

  _Follow(this._userId, this._userItem);

  bool get _following => _userItem['isFollowing'];

  int get _otherUserId => _userItem['user']['id'];

  String get _username => _userItem['user']['username'];

  @override
  Widget build(BuildContext context) {
    return Mutation(
      options: MutationOptions(
          document: _following ? mutation.unfollowUser : mutation.followUser),
      builder: (RunMutation toggleFollow, QueryResult queryResult) {
        return ListTile(
          title: Text(_username),
          trailing: Icon(Icons.favorite, color: _following ? Colors.red : null),
          onTap: () => toggleFollow({
            'userId': _userId,
            'otherUserId': _otherUserId,
          }),
        );
      },
      update: (Cache cache, QueryResult result) {
        cache.reset();
      },
    );
  }
}
