import 'package:flutter/material.dart';
import 'package:graphql_flutter/graphql_flutter.dart';
import 'package:intl/intl.dart';

import '../domain/domain.dart' as domain;
import '../operations/queries/queries.dart' as query;

/// Widget which shows posts written by followed users and the user themselves.
class Timeline extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Query(
      options: QueryOptions(
        document: query.getTimelineWithCurrentUser,
        pollInterval: 60,
      ),
      builder: (QueryResult result, {BoolCallback refetch}) {
        return RefreshIndicator(
          child: _TimelineList(result),
          onRefresh: () async {
            refetch();
          },
        );
      },
    );
  }
}

/// The widget that builds the timeline
class _TimelineList extends StatelessWidget {
  final QueryResult result;

  _TimelineList(this.result);

  @override
  Widget build(BuildContext context) {
    if (result.hasErrors) {
      return Text(result.errors.toString());
    }
    if (result.loading) {
      return Center(
        child: CircularProgressIndicator(),
      );
    }
    var timeline = domain.Timeline.fromQuery(result.data);
    var username = domain.User.fromQuery(result.data['currentUser']).username;
    return ListView.builder(
      physics: BouncingScrollPhysics(),
      itemCount: timeline.length,
      itemBuilder: (context, index) {
        return _TimelinePost(timeline[index], username);
      },
    );
  }
}

/// Widget that presents a single post
class _TimelinePost extends StatelessWidget {
  // The username of the logged in user
  final String _username;
  final domain.TimelineItem _timelineItem;

  _TimelinePost(this._timelineItem, this._username);

  String _toTimeAndDate(DateTime dateTime) {
    return '${DateFormat.yMd().format(dateTime)} ${DateFormat.Hm().format(dateTime)}';
  }

  get isOwn => _username == _timelineItem.user.username;

  BorderRadius cardBorder() {
    return isOwn
        ? BorderRadius.only(
            topLeft: Radius.circular(10),
            bottomLeft: Radius.circular(10),
            bottomRight: Radius.circular(10),
          )
        : BorderRadius.only(
            topRight: Radius.circular(10),
            bottomLeft: Radius.circular(10),
            bottomRight: Radius.circular(10),
          );
  }

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: EdgeInsets.symmetric(
        horizontal: 8.0,
        vertical: 5.0,
      ),
      color: isOwn ? Theme.of(context).primaryColorLight : null,
      shape: RoundedRectangleBorder(
        borderRadius: cardBorder(),
      ),
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.only(bottom: 8.0),
              child: Row(
                children: <Widget>[
                  Text(_timelineItem.user.fullName),
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 8.0),
                    child: Text(
                      '${_timelineItem.user.handle}',
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  )
                ],
              ),
            ),
            Padding(
              padding: const EdgeInsets.only(
                top: 5.0,
                left: 5.0,
                right: 5.0,
                bottom: 10.0,
              ),
              child: Text(
                _timelineItem.post.message,
                style: TextStyle(
                  fontSize: 15.5,
                ),
              ),
            ),
            Container(
              alignment: FractionalOffset.centerRight,
              child: Text(
                _toTimeAndDate(_timelineItem.post.creationDate),
                style: TextStyle(
                  fontStyle: FontStyle.italic,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
