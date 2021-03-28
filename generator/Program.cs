using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Xml;
using System.Xml.Schema;

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

        private static void DoFile(string file)
        {
            var fileInfo = new FileInfo(file);
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
