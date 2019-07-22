import 'package:flutter/material.dart';
import 'package:frontend/widgets/post.dart';
import 'package:graphql_flutter/graphql_flutter.dart';

import 'common/config.dart' as config;
import 'common/token.dart';
import 'widgets/timeline.dart';
import 'widgets/users.dart';

main() async {
  final AuthLink authLink =
      AuthLink(getToken: () async => 'Bearer ${await fetchAuthToken()}');
  final HttpLink httpLink = HttpLink(uri: config.graphQlApiUri);
  final Link link = authLink.concat(httpLink);
  final ValueNotifier<GraphQLClient> client = ValueNotifier(
    GraphQLClient(
      link: link,
      cache: InMemoryCache(),
    ),
  );
  runApp(
    GraphQLProvider(
      client: client,
      child: App(),
    ),
  );
}

class App extends StatelessWidget {
  @override
  Widget build(BuildContext context) => MaterialApp(
        title: 'FSE Frontend App',
        home: AppHome(),
      );
}

class AppHome extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _AppHomeState();
  }
}

class _AppHomeState extends State<AppHome> {
  int _index = 0;

  final String _title = 'Full Stack Example';

  final List<Widget> _views = [
    Timeline(),
    UserList(),
  ];

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
