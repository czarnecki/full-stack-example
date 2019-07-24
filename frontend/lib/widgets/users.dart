import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
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
        var users = domain.UserList.fromQuery(result.data);
        return ListView.separated(
          itemCount: users.length,
          itemBuilder: (context, index) {
            return _Follow(users[index], refetch);
          },
          separatorBuilder: (BuildContext context, int index) {
            return Divider(
              indent: 50,
              endIndent: 50,
              color: Theme.of(context).colorScheme.onSurface,
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
              Text(
                '${_userItem.user.givenName} ${_userItem.user.familyName}',
              ),
            ],
          ),
          subtitle: Text(_userItem.user.handle),
          trailing: _userItem.isFollowing
              ? const Icon(
                  FontAwesomeIcons.asterisk,
                  color: Colors.blue,
                )
              : const Icon(
                  FontAwesomeIcons.asterisk,
                ),
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
