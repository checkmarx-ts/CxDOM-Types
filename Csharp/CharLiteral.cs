using System;

namespace HelloWorld
{
  class Program
  {
    static void Main(string[] args)
    {
        var chars = new[]
        {
            'j',
            '\u006A',
            '\x006A',
            (char)106,
        };
        Console.WriteLine(string.Join(" ", chars));  // output: j j j j   
    }
  }
}