using System;
using System.Collections.Generic;
using System.IO;
using System.Xml;

namespace Generator
{
    public class Program
    {

        public static void Main(string[] args)
        {
            const string input = @"G:\workspace\Chinese-Law\documents";
            foreach (var file in Directory.GetFiles(input))
            {
                DoFile(file);
            }

            foreach (var directory in Directory.GetDirectories(input))
            {
                DoDirectory(directory);
            }
        }

        private static void DoDirectory(string input)
        {
            foreach (var file in Directory.GetFiles(input))
            {
                DoFile(file);
            }

            foreach (var directory in Directory.GetDirectories(input))
            {
                DoDirectory(directory);
            }
        }

        private static readonly Dictionary<string, string> names = new Dictionary<string, string>();

        private static void DoFile(string file)
        {
            var fileInfo = new FileInfo(file);
            if (names.TryGetValue(fileInfo.Name, out var old))
            {
                Console.WriteLine($"重复文件{file} {old}");
                return;
            }
            names.Add(fileInfo.Name, file);
            if (fileInfo.Length == 0)
            {
                return;
            }
            var doc = new XmlDocument();
            doc.Load(file);
            try
            {
                Validator.Process(doc.DocumentElement);
            }
            catch (XmlException e)
            {
                Console.WriteLine($"{file} : {e.Message}");
            }
        }

    }
}
