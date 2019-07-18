import 'package:flutter/material.dart';
import 'package:frontend/operations/mutations/mutations.dart' as mutation;
import 'package:graphql_flutter/graphql_flutter.dart';
import 'package:frontend/operations/queries/queries.dart' as query;

class UserList extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return _UserListQuery();
  }
}

class _UserListQuery extends StatelessWidget {
  static final _userId = 20;
  @override
  Widget build(BuildContext context) {
    return Query(
      options: QueryOptions(
        fetchPolicy: FetchPolicy.networkOnly,
        document: query.getUsers,
        variables: {
          'userId' : _userId
        }
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
        List users = result.data['getUserListWithFollowStatus']
            ['otherUsersWithFollowStatus'];
        return ListView.separated(
          itemBuilder: (context, index) {
            final userItem = users[index];
            return ListTile(
              title: Text(userItem['user']['username']),
              trailing: _FollowMutation(
                20,
                userItem['user']['id'],
                userItem['isFollowing'],
              ),
            );
          },
          separatorBuilder: (context, index) => Divider(),
          itemCount: users.length,
        );
      },
    );
  }
}

class _FollowMutation extends StatefulWidget {
  final int _userId;
  final int _otherUserId;
  final bool _following;

  _FollowMutation(this._userId, this._otherUserId, this._following);

  @override
  State<StatefulWidget> createState() {
    return _FollowState(_userId, _otherUserId, _following);
  }
}

class _FollowState extends State<_FollowMutation> {
  int _userId;
  int _otherUserId;
  bool _following;

  _FollowState(this._userId, this._otherUserId, this._following);

  @override
  Widget build(BuildContext context) {
    return Mutation(
      options: MutationOptions(
          document: _following ? mutation.unfollowUser : mutation.followUser),
      builder: (RunMutation runMutation, QueryResult queryResult) {
        return IconButton(
          icon: Icon(Icons.favorite, color: _following ? Colors.red : null),
          onPressed: () => runMutation({
            'userId': _userId,
            'otherUserId': _otherUserId,
          }),
        );
      },
      update: (Cache cache, QueryResult result) {
        if (!result.hasErrors) {
          setState(() {
            _following = _following ? false : true;
          });
        }
      },
    );
  }
}
