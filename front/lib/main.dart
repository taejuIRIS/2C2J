import 'package:flutter/material.dart';
import 'package:todotodo/screens/home_page.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text('TODOTODO'),
        ),
        body: SingleChildScrollView(
          child: HomePage(),
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            print('투두리스트 추가');
          },
          child: Icon(Icons.add),
        ),
        floatingActionButtonLocation: FloatingActionButtonLocation.endFloat,
      ),
    );
  }
}
