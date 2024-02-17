

import 'package:flutter/material.dart';

class TodoListWithCheckboxes extends StatelessWidget {
  final List<String> todos;

  TodoListWithCheckboxes({required this.todos});

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      shrinkWrap: true,
      itemCount: todos.length,
      itemBuilder: (context, index) {
        return ListTile(
          title: Row(
            children: [
              Text(todos[index]),
              Spacer(),
              IconButton(
                icon: Icon(Icons.check_circle_outline),
                onPressed: () {
                  print('할일 완료: ${todos[index]}');
                },
              ),
              IconButton(
                icon: Icon(Icons.cancel_outlined),
                onPressed: () {
                  print('할일 취소: ${todos[index]}');
                },
              ),
            ],
          ),
        );
      },
    );
  }
}
