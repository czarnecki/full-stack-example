import 'package:flutter/material.dart';
import 'package:frontend/operations/queries/queries.dart' as query;
import 'package:graphql_flutter/graphql_flutter.dart';

class Timeline extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return _TimelineQuery();
  }
}

class _TimelineQuery extends StatelessWidget {
  static final _userId = 20;

  @override
  Widget build(BuildContext context) {
    return Query(
      options: QueryOptions(
        document: query.getTimeline,
        variables: {
          'userId': _userId,
        },
        pollInterval: 10,
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
        List timeline = result.data['action']['timeline'];
        return ListView.separated(
          separatorBuilder: (context, index) => Divider(),
          itemCount: timeline.length,
          itemBuilder: (context, index) {
            final timelineItem = timeline[index];
            return Column(
              children: [
                Text(
                  timelineItem['post']['message'],
                  textAlign: TextAlign.start,
                ),
                Text(
                  timelineItem['user']['username'],
                  textAlign: TextAlign.end,
                ),
              ],
            );
          },
        );
      },
    );
  }
}
