import 'package:flutter/material.dart';

import 'post.dart';
import 'timeline.dart';
import 'users.dart';

/// Widget which provides [Scaffold] and allows accessing other views.
class Home extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _HomeState();
  }
}

class _HomeState extends State<Home> {
  /// Selected index
  int _index = 0;

  final String _title = 'Full Stack Example';

  /// Views which can be shown
  final List<Widget> _views = [
    Timeline(),
    UserList(),
  ];

  /// Sets the index when user selects a different tab
  _onTappedIcon(int index) {
    setState(() {
      this._index = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(_title),
      ),
      resizeToAvoidBottomPadding: false,
      floatingActionButton: Post(),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
      bottomNavigationBar: BottomAppBar(
        shape: CircularNotchedRectangle(),
        clipBehavior: Clip.antiAlias,
        child: BottomNavigationBar(
          currentIndex: _index,
          onTap: _onTappedIcon,
          items: [
            BottomNavigationBarItem(
              icon: Icon(Icons.timeline),
              title: Text('Timeline'),
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.list),
              title: Text('Users'),
            ),
          ],
        ),
      ),
      body: _views[_index],
    );
  }
}
