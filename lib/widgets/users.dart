import 'package:flutter/material.dart';
import 'package:graphql_flutter/graphql_flutter.dart';

import '../operations/mutations/mutations.dart' as mutation;
import '../operations/queries/queries.dart' as query;

class UserList extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Query(
      options: QueryOptions(
        document: query.getUsers,
      ),
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
            return _Follow(users[index], refetch);
          },
        );
      },
    );
  }
}

class _Follow extends StatelessWidget {
  final Map<String, dynamic> _userItem;
  final BoolCallback refetch;

  _Follow(this._userItem, this.refetch);

  bool get _following => _userItem['isFollowing'];

  String get _username => _userItem['user']['username'];

  @override
  Widget build(BuildContext context) {
    return Mutation(
      options: MutationOptions(
          document: _following ? mutation.unfollow : mutation.follow),
      builder: (RunMutation toggleFollow, QueryResult queryResult) {
        return ListTile(
          title: Text(_username),
          trailing: Icon(Icons.favorite, color: _following ? Colors.red : null),
          onTap: () => toggleFollow({
            'followedUserUsername': _username,
          }),
        );
      },
      update: (Cache cache, QueryResult result) {
        refetch();
      },
    );
  }
}
