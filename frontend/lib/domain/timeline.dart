import 'package:graphql_flutter/graphql_flutter.dart';

import 'post.dart';
import 'user.dart';

class Timeline {
  final List<TimelineItem> timelineItems;

  Timeline._(this.timelineItems);

  static Timeline fromQuery(QueryResult result) {
    List queryTimelineItem = result.data['action']['timeline'];
    var timelineItems = queryTimelineItem.map(TimelineItem.fromQuery).toList();
    return Timeline._(timelineItems);
  }
}

class TimelineItem {
  final Post post;
  final User user;

  TimelineItem._(this.post, this.user);

  static TimelineItem fromQuery(dynamic data) {
    var user = User.fromQuery(data);
    var post = Post.fromQuery(data);
    return TimelineItem._(post, user);
  }
}
