import 'package:flutter/material.dart';
import 'package:frontend/operations/mutations/mutations.dart' as mutation;
import 'package:frontend/operations/queries/queries.dart' as query;
import 'package:graphql_flutter/graphql_flutter.dart';

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
            return _Follow(users[index]);
          },
        );
      },
    );
  }
}

class _Follow extends StatelessWidget {
  final Map<String, dynamic> _userItem;

  _Follow(this._userItem);

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
        cache.reset();
      },
    );
  }
}
