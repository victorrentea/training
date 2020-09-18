using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace VideoStore
{
    internal class Class1
    {
        public static void Main(string[] args)
        {
            new EmailService().SendOrderReceivedEmail("a@b.com");
            Console.ReadLine();
        }
    }

    internal class EmailService
    {
        public void SendOrderReceivedEmail(string emailAddress)
        {
            var context = new EmailContext( /*smtpConfig,etc*/);
            var MAX_RETRIES = 3;
            for (var i = 0; i < MAX_RETRIES; i++)
            {
                var email = new Email(); // constructor generates new unique 
                email.sender = "noreply@corp.com";
                email.replyTo = "/dev/null";
                email.to = emailAddress;
                email.subject = "Order Received";
                email.body = "Thank you for your order";
                var success = context.send(email);
                if (success) break;
            }
        }

        private class EmailContext
        {
            private readonly Random rand = new Random();

            public bool send(Email email)
            {
                Console.Out.WriteLine("Trying to send " + email.asText());
                return rand.Next() % 2 == 0;
            }
        }

        private class Email
        {
            private readonly int id = new Random().Next();
            public string subject { get; set; }
            public string body;
            public string sender;
            public string replyTo;
            public string to;

            public string asText()
            {
                return $"Email({subject}, {body}, {id})";
            }
        }
    }
}