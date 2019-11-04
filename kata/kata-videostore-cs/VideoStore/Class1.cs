using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace VideoStore
{
    class Class1
    {
        public static void Main(string[] args)
        {
            new EmailService().SendOrderReceivedEmail("a@b.com");
            Console.ReadLine();
        }
    }

    class EmailService
    {

        public void SendOrderReceivedEmail(String emailAddress)
        {
            EmailContext context = new EmailContext(/*smtpConfig,etc*/);
            int MAX_RETRIES = 3;
            for (int i = 0; i < MAX_RETRIES; i++)
            {
                Email email = new Email(); // constructor generates new unique 
                email.sender = "noreply@corp.com";
                email.replyTo = "/dev/null";
                email.to = emailAddress;
                email.subject = "Order Received";
                email.body = "Thank you for your order";
                bool success = context.send(email);
                if (success) break;
            }
        }
        class EmailContext
        {
            private readonly Random rand = new Random();
            public bool send(Email email)
            {
                Console.Out.WriteLine("Trying to send " + email.asText());
                return rand.Next() % 2 == 0;
            }
        }

        class Email
        {
            private readonly int id = new Random().Next();
            public String subject { get; set; }            
            public String body;
            public String sender;
            public String replyTo;
            public String to;
            
            public string asText()
            {
                return $"Email({subject}, {body}, {id})";
            }

        }
    }
  
}