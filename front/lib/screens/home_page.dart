import 'package:flutter/material.dart';
import '../widget/todolist_with_checkboxes.dart';

class HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        Column(
          children: [
            Container(
              height: 100,
              child: Center(
                child: Text(
                  '뇌',
                  style: TextStyle(fontSize: 40),
                ),
              ),
            ),
            Container(
              height: 40,
              child: Row(
                children: [
                  Padding(
                    padding: EdgeInsets.fromLTRB(8.0, 10.0, 8.0, 10.0),
                    child: Container(
                      width: 500,
                      height: 20,
                      decoration: BoxDecoration(
                        gradient: LinearGradient(
                          begin: Alignment.centerLeft,
                          end: Alignment.centerRight,
                          colors: [
                            Colors.green,
                            Colors.yellow,
                            Colors.red,
                          ],
                        ),
                        borderRadius: BorderRadius.circular(10),
                      ),
                    ),
                  ),
                ],
              ),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                Text('쾌적'),
                Icon(Icons.arrow_drop_up),
                Text('위험'),
              ],
            ),
            SizedBox(height: 10),
          ],
        ),
        Container(
          height: 50,
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: [
              Text('월'),
              Text('화'),
              Text('수'),
              Text('목'),
              Text('금'),
              Text('토'),
              Text('일'),
            ],
          ),
        ),
        Container(
          height: 50,
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: List.generate(7, (index) {
              return CircleAvatar(
                backgroundColor: Colors.grey,
                radius: 15,
              );
            }),
          ),
        ),
        Container(
          padding: EdgeInsets.all(10),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              Text('첫 번째 투두리스트', style: TextStyle(fontSize: 18)),
              SizedBox(height: 5),
              TodoListWithCheckboxes(
                todos: ['할일1', '할일2', '할일3','4'], // 첫 번째 투두리스트 목록
              ),
            ],
          ),
        ),
        Container(
          padding: EdgeInsets.all(10),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              Text('두 번째 투두리스트', style: TextStyle(fontSize: 18)),
              SizedBox(height: 5),
              TodoListWithCheckboxes(
                todos: ['할일4', '할일5'], // 두 번째 투두리스트 목록
              ),
            ],
          ),
        ),
      ],
    );
  }
}
