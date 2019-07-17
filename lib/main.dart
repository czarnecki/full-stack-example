import 'package:flutter/material.dart';
import 'package:graphql_flutter/graphql_flutter.dart';

import 'common/config.dart' as config;
import 'common/token.dart';
import 'widgets/timeline.dart';

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
    TimelineWidget(),
    Center(
      child: Text('test'),
    )
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Test'),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: null,
        child: Icon(Icons.add),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
      bottomNavigationBar: BottomAppBar(
        notchMargin: 4.0,
        shape: CircularNotchedRectangle(),
        child: Row(
          mainAxisSize: MainAxisSize.max,
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: <Widget>[
            IconButton(
              icon: Icon(Icons.timeline),
              onPressed: () => _pageController.jumpToPage(0),
            ),
            IconButton(
              icon: Icon(Icons.timeline),
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
