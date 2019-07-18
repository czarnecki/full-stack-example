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
      cache: NormalizedInMemoryCache(
        dataIdFromObject: typenameDataIdFromObject,
      ),
    ),
  );
  runApp(
    GraphQLProvider(
      client: client,
      child: MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'FSE Frontend App',
      home: MyAppStateWidget(),
    );
  }
}

class MyAppStateWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _MyAppState();
}

class _MyAppState extends State<MyAppStateWidget> {
  PageController _pageController = PageController(initialPage: 0);
  List<Widget> _views = [
    Timeline(),
    UserList(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Test'),
      ),
      floatingActionButton: Post(),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
      bottomNavigationBar: BottomAppBar(
        shape: CircularNotchedRectangle(),
        child: Row(
          mainAxisSize: MainAxisSize.max,
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: <Widget>[
            IconButton(
              tooltip: 'Timeline',
              icon: Icon(Icons.timeline),
              onPressed: () => _pageController.jumpToPage(0),
            ),
            IconButton(
              tooltip: 'Users',
              icon: Icon(Icons.list),
              onPressed: () => _pageController.jumpToPage(1),
            ),
          ],
        ),
      ),
      body: PageView(
        controller: _pageController,
        children: _views,
      ),
    );
  }
}
