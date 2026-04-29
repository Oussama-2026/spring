import 'package:flutter/material.dart';
import 'screens/products_screen.dart';

void main() {
  runApp(const SMSApp());
}

class SMSApp extends StatelessWidget {
  const SMSApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'SMS - Store Management',
      debugShowCheckedModeBanner: false,
      theme: ThemeData.dark().copyWith(
        scaffoldBackgroundColor: const Color(0xFF0D1B2A),
        colorScheme: const ColorScheme.dark(
          primary: Color(0xFF4FC3F7),
          surface: Color(0xFF1E2A3A),
        ),
      ),
      home: const ProductsScreen(),
    );
  }
}
