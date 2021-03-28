using System.Collections.Generic;
using System.Text.RegularExpressions;
using System.Xml;

namespace Generator
{

    public class Validator
    {

        private static readonly ValidatorNode root;

        static Validator()
        {
            root = new ValidatorNode("document");

            var histories = new ValidatorNode("histories");
            var revision = new ValidatorNode("revision");
            var createDate = new ValidatorNode("create-date");
            var organization = new ValidatorNode("organization");
            var notice = new ValidatorNode("notice");
            var id = new ValidatorNode("id");
            var executeDate = new ValidatorNode("execute-date");

            var amendment = new ValidatorNode("amendment");

            root.AddChild(new ValidatorChild(histories)
            {
                Required = true,
            });

            histories.AddChild(new ValidatorChild(revision)
            {
                Required = true,
            });
            histories.AddChild(new ValidatorChild(revision)
            {
                Required = false,
                Any = true,
            });
            histories.AddChild(new ValidatorChild(amendment)
            {
                Required = false,
                Any = true,
            });

            revision.AddChild(new ValidatorChild(createDate)
            {
                Required = true
            });
            revision.AddChild(new ValidatorChild(organization)
            {
                Required = true,
            });
            revision.AddChild(new ValidatorChild(executeDate)
            {
                Required = true,
            });

            amendment.AddChild(new ValidatorChild(createDate)
            {
                Required = true
            });
            amendment.AddChild(new ValidatorChild(organization)
            {
                Required = true,
            });
            amendment.AddChild(new ValidatorChild(notice)
            {
                Required = true,
            });
            amendment.AddChild(new ValidatorChild(id)
            {
                Required = false,
            });
            amendment.AddChild(new ValidatorChild(executeDate)
            {
                Required = true,
            });

            const string datePattern = "[0-9]{4}年([1-9]|1[0-2])月([1-9]|[1-2][0-9]|3[0-1])日";
            createDate.SetBody(datePattern);
            organization.SetBody("");
            notice.SetBody("");
            id.SetBody("");
            executeDate.SetBody(datePattern);
        }

        public static void Process(XmlElement e)
        {
            root.Validate(e, 0);
        }

    }

    public class ValidatorNode
    {

        public readonly string Name;
        private readonly List<ValidatorChild> children = new List<ValidatorChild>();
        private string match;

        public ValidatorNode(string name)
        {
            Name = name;
        }

        public void Validate(XmlElement element, int position)
        {
            if (element.Name != Name)
            {
                throw new XmlException($"element name invalid <{element.Name}>");
            }

            if (match != null)
            {
                if (match.Length > 0 && !Regex.IsMatch(element.InnerXml, match))
                {
                    throw new XmlException($"<{element.Name}> text invalid {element.InnerXml}");
                }
                return;
            }

            var iChild = 0;
            for (var index = 0; index < element.ChildNodes.Count; )
            {
                var childNode = (XmlElement) element.ChildNodes[index];

                if (iChild >= children.Count)
                {
                    throw new XmlException($"unknown element <{childNode.Name}> in <{Name}>");
                }

                var validateNode = children[iChild];
                var lastValidateNode = iChild > 0 ? children[iChild - 1] : null;
                if (validateNode.Name == childNode.Name)
                {
                    validateNode.Validate(childNode, index);
                    index++;
                    if (validateNode.Any)
                    {
                        continue;
                    }
                }
                else if (lastValidateNode != null && lastValidateNode.Any)
                {
                    iChild--;
                    continue;
                }
                else if (validateNode.Required)
                {
                    throw new XmlException($"should <{validateNode.Name}> but <{childNode.Name}> in <{element.Name}>[{index}]");
                }

                iChild++;
            }

            for (; iChild < children.Count; iChild++)
            {
                if (children[iChild].Required)
                {
                    throw new XmlException($"should <{children[iChild].Name}> but end tag in <{element.Name}>[{position}]");
                }
            }
        }

        public void AddChild(ValidatorChild child)
        {
            children.Add(child);
        }

        public void SetBody(string match)
        {
            this.match = match;
        }

    }

    public class ValidatorChild
    {

        public bool Required = true;
        public bool Any = false;

        private readonly ValidatorNode node;

        public string Name => node.Name;

        public ValidatorChild(ValidatorNode node)
        {
            this.node = node;
        }

        public void Validate(XmlElement element, int position)
        {
            node.Validate(element, position);
        }
    }

}
