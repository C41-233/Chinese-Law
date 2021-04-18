using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Generator
{
    public static class Utils
    {

        public static string RemoveLast(this string self, string suffix)
        {
            return self.Substring(0, self.Length - suffix.Length);
        }

    }
}
