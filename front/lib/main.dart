import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

import 'database/database_helper.dart';
import 'database/routine.dart';
void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: HomeScreen(),
    );
  }
}

class HomeScreen extends StatefulWidget {
  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  List<Routine> todayRoutines = []; // 오늘의 루틴을 저장할 리스트

  @override
  void initState() {
    super.initState();
    loadTodayRoutines(); // initState에서 오늘의 루틴을 불러옴
  }

  Future<void> loadTodayRoutines() async {
    await DatabaseHelper.instance.init(); // 여기서 데이터베이스 초기화를 기다립니다.

    DateTime today = DateTime.now();
    DateTime yesterday = today.subtract(Duration(days: 1));
    //List<Routine> routines = await DatabaseHelper.instance.getDailyRoutines(today);
    List<Routine> routines = await DatabaseHelper.instance.getDailyRoutines(today);
    todayRoutines = routines;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('오늘의 루틴'),
      ),
      body: ListView.builder(
        itemCount: todayRoutines.length,
        itemBuilder: (context, index) {
          return ListTile(
            title: Text(todayRoutines[index].title),
            subtitle: Column(
              children: [
                Text(todayRoutines[index].content),
                Text(todayRoutines[index].startDate.toString()),
                Text(todayRoutines[index].endDate.toString()),
              ],
            ),
          );
        },
      ),
    );
  }
}
