import 'package:flutter/material.dart';
import 'package:graphql_flutter/graphql_flutter.dart';
import 'package:intl/intl.dart';

import '../domain/domain.dart' as domain;
import '../operations/queries/queries.dart' as query;

class Timeline extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return _TimelineQuery();
  }
}

class _TimelineQuery extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Query(
      options: QueryOptions(
        document: query.getTimeline,
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
    return ListView.builder(
      physics: BouncingScrollPhysics(),
      itemCount: timeline.length,
      itemBuilder: (context, index) {
        return _TimelineCard(timeline[index]);
      },
    );
  }
}

class _TimelineCard extends StatelessWidget {
  final domain.TimelineItem _timelineItem;

  _TimelineCard(this._timelineItem);

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: EdgeInsets.symmetric(
        horizontal: 8.0,
        vertical: 5.0,
      ),
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(5.5),
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
                      '\$${_timelineItem.user.username}',
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
                DateFormat.yMMMEd()
                    .format(_timelineItem.post.creationDate.toLocal()),
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
