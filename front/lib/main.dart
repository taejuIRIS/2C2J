import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:todotodo/database/routine_db_provider.dart';
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
  final RoutineDBProvider routineDBProvider = RoutineDBProvider();

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  List<Routine> todayRoutines = []; // 오늘의 루틴을 저장할 리스트

  @override
  void initState() {
    super.initState();
    loadTodayRoutines();
    loadRoutineLogs(); // initState에서 오늘의 루틴을 불러옴
  }

  Future<void> loadTodayRoutines() async {
    DateTime today = DateTime.now();
    //List<Routine> routines = await DatabaseHelper.instance.getDailyRoutines(today);
    List<Routine> routines = await widget.routineDBProvider.getDailyRoutines(today);
    setState(() {
      todayRoutines = routines;
    });
  }

  Future<void> loadRoutineLogs() async {
    widget.routineDBProvider.insertLogUntilToday(1);
    widget.routineDBProvider.insertLogUntilToday(2);
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
                    future: widget.routineDBProvider.getRoutineLogs(todayRoutines[index].num!),
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
                            itemBuilder: (context, index) {
                              return ListTile(
                                title: Column(
                                  children: [
                                    Text(routineLogs![index].date.toString())
                                  ],
                                ),
                              );
                            },
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
