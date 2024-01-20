import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

import 'database/database_helper.dart';
import 'database/routine.dart';
import 'database/routine_log.dart';
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
    initDB();
    loadTodayRoutines();
    loadRoutineLogs();// initState에서 오늘의 루틴을 불러옴
  }

  Future<void> initDB() async{
    await DatabaseHelper.instance.init();
  }
  Future<void> loadTodayRoutines() async {// 여기서 데이터베이스 초기화를 기다립니다.

    DateTime today = DateTime.now();
    DateTime yesterday = today.subtract(Duration(days: 1));
    //List<Routine> routines = await DatabaseHelper.instance.getDailyRoutines(today);
    List<Routine> routines = await DatabaseHelper.instance.getDailyRoutines(today);
    todayRoutines = routines;
  }
  Future<void> loadRoutineLogs() async{
    DatabaseHelper.instance.insertLogUntilToday(1);
    DatabaseHelper.instance.insertLogUntilToday(2);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('루틴 목록'),
      ),
      body: ListView.builder(
        itemCount: todayRoutines.length,
        itemBuilder: (context, index) {
          return ListTile(
            title: Text(todayRoutines[index].title),
            subtitle: SizedBox(
              height: 300,
              child: Column(
                mainAxisAlignment: MainAxisAlignment.start,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(todayRoutines[index].content),
                  FutureBuilder<List<RoutineLog>>(
                    future: DatabaseHelper.instance.getRoutineLogs(todayRoutines[index].num!),
                    builder: (context, snapshot) {
                      if (snapshot.connectionState == ConnectionState.waiting) {
                        return CircularProgressIndicator(); // 로딩 중 표시
                      } else if (snapshot.hasError) {
                        return Text(snapshot.error.toString());
                      } else {
                        List<RoutineLog>? routineLogs = snapshot.data;
                        print(snapshot.data);
                        return Expanded(
                          child: ListView.builder(
                              itemCount: routineLogs?.length,
                              itemBuilder: (context,index){
                                return ListTile(
                                  title: Column(
                                    children: [
                                      Text(routineLogs![index].date.toString())
                                    ],
                                  ),
                                );
                              }
                          ),
                        );
                      }
                    },
                  )
                ],
              ),
            ),
          );
        },
      ),
    );
  }
}
