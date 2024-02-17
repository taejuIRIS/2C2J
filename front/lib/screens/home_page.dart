import 'package:flutter/material.dart';
import '../widget/todolist_with_checkboxes.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int brainImageState = 3; // 초기 이미지 상태 3

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
                // 이미지 상태에 따라 이미지 변경
                child: Image.asset(getBrainImagePath(brainImageState)),
              ),
            ),
            Container(
              height: 40,
              child: Row(
                children: [
                  Padding(
                    padding: EdgeInsets.fromLTRB(8.0, 10.0, 8.0, 10.0),
                    child: Container(
                      height: 20,
                      width: MediaQuery.of(context).size.width - 32,
                      decoration: BoxDecoration(
                        border: Border.all(color: Colors.black, width: 1),
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

            const Row(
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
                todos: ['할일1', '할일2', '할일3', '4'], // 첫 번째 투두리스트 목록
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

  // 상태에 따른 이미지 경로 설정
  String getBrainImagePath(int state) {
    return 'assets/brain$state.png';
  }

  // 이미지 상태 변경
  void changeBrainImageState() {
    setState(() {
      if (brainImageState < 5) {
        brainImageState++; // 다음 이미지 상태로 변경
      } else {
        brainImageState = 1; // 이미지 상태가 5이면 다시 1로 변경
      }
    });
  }
}
