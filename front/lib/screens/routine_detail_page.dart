import 'package:flutter/material.dart';
import 'package:table_calendar/table_calendar.dart';
import 'package:todotodo/my_style.dart';

class RoutineDetailPage extends StatefulWidget {
  RoutineDetailPage({super.key});
  //int routineID;
  @override
  State<RoutineDetailPage> createState() => _RoutineDetailPageState();
}

class _RoutineDetailPageState extends State<RoutineDetailPage> {

  ///data base에서 로딩하기 by routineID
  ///FutureBuilder 적용하기
  Map<DateTime, bool> routineDone = {
    DateTime.utc(2024, 2, 20): true,
    DateTime.utc(2024, 2, 23): true,
  };

  //int id;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('루틴',style: MyTextStyles.h1,),
            Text('30일 연속 성공',style: MyTextStyles.h2,),
            Text('시작일 2022/22/22', style: MyTextStyles.s1,),
            TableCalendar(
              firstDay: DateTime.utc(2010, 10, 16),
              lastDay: DateTime.utc(2030, 3, 14),
              focusedDay: DateTime.now(),
              eventLoader: (day) {
                // 해당 날짜에 루틴이 수행되었는지에 따라 마커를 반환
                if (routineDone[day] != null && routineDone[day] == true) {
                  return ['Done'];
                }
                return [];
              },
              calendarBuilders: CalendarBuilders(
                markerBuilder: (context, date, events) {
                  if (events.isNotEmpty) {
                    // 마커 표시 (여기서는 간단한 동그라미로 표시)
                    return Positioned(
                      right: 1,
                      bottom: 1,
                      child: Container(
                        decoration: BoxDecoration(
                          shape: BoxShape.circle,
                          color: Colors.blue,
                        ),
                        width: 8,
                        height: 8,
                      ),
                    );
                  }
                  return null;
                },
              ),
            )
          ],
        ),
      ),
    );
  }
}