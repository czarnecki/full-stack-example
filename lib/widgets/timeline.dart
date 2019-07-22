import 'package:flutter/material.dart';
import 'package:frontend/operations/queries/queries.dart' as query;
import 'package:graphql_flutter/graphql_flutter.dart';
import 'package:intl/intl.dart';

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
    List timeline = result.data['action']['timeline'];
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
  final Map<String, dynamic> _timelineItem;

  _TimelineCard(this._timelineItem);

  String get _username => _timelineItem['user']['username'];

  String get _message => _timelineItem['post']['message'];

  String get _creationDate => _timelineItem['post']['creationDate'];

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
              padding: const EdgeInsets.only(
                top: 5.0,
                left: 5.0,
                right: 5.0,
                bottom: 10.0,
              ),
              child: Text(
                _message,
                style: TextStyle(
                  fontSize: 15.5,
                ),
              ),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: <Widget>[
                Text(
                  '@$_username',
                  style: TextStyle(
                    fontStyle: FontStyle.italic,
                  ),
                ),
                Text(
                  DateFormat.yMMMEd()
                      .format(DateTime.parse(_creationDate).toLocal()),
                  style: TextStyle(
                    fontStyle: FontStyle.italic,
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
