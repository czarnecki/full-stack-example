import 'package:flutter/material.dart';
import 'package:graphql_flutter/graphql_flutter.dart';

import '../domain/domain.dart' as domain;
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
        var userList = domain.UserList.fromQuery(result);
        return ListView.separated(
          itemCount: userList.users.length,
          itemBuilder: (context, index) {
            return _Follow(userList.users[index], refetch);
          },
          separatorBuilder: (BuildContext context, int index) {
            return Divider(
              color: Colors.black,
            );
          },
        );
      },
    );
  }
}

class _Follow extends StatelessWidget {
  final domain.UserListItem _userItem;
  final BoolCallback refetch;

  _Follow(this._userItem, this.refetch);

  @override
  Widget build(BuildContext context) {
    return Mutation(
      options: MutationOptions(
        document: _userItem.isFollowing ? mutation.unfollow : mutation.follow,
      ),
      builder: (RunMutation toggleFollow, QueryResult queryResult) {
        return ListTile(
          title: Row(
            children: <Widget>[
              Text('${_userItem.user.givenName} ${_userItem.user.familyName}'),
              Padding(
                padding: const EdgeInsets.only(left: 8.0),
                child: Text(
                  '\$${_userItem.user.username}',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                  ),
                ),
              )
            ],
          ),
          trailing: _userItem.isFollowing
              ? const Icon(
                  Icons.star,
                  color: Colors.amber,
                )
              : const Icon(Icons.star_border),
          onTap: () => toggleFollow({
            'followedUserUsername': _userItem.user.username,
          }),
        );
      },
      update: (Cache cache, QueryResult result) {
        refetch();
      },
    );
  }
}
