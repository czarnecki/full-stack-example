import 'post.dart';
import 'user.dart';

class Timeline {
  static List<TimelineItem> fromQuery(dynamic data) {
    List queryTimelineItem = data['action']['timeline'];
    var timelineItems = queryTimelineItem.map(TimelineItem.fromQuery).toList();
    return timelineItems;
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
