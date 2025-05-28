-- Insert basic roles
INSERT INTO roles (authority) VALUES
  ('USER'),
  ('ADMIN');

-- Insert categories
INSERT INTO category (name, description, slug) VALUES
   ('Frontend Frameworks', 'JavaScript libraries and frameworks for building user interfaces and single-page applications', 'frontend-frameworks'),
   ('Backend Frameworks', 'Server-side frameworks for building APIs, web applications, and microservices', 'backend-frameworks'),
   ('Databases', 'Relational and NoSQL databases for data storage and management', 'databases'),
   ('Cloud Platforms', 'Cloud computing platforms and infrastructure services', 'cloud-platforms'),
   ('DevOps Tools', 'Tools for continuous integration, deployment, and infrastructure management', 'devops-tools'),
   ('Mobile Development', 'Frameworks and tools for building mobile applications', 'mobile-development'),
   ('Programming Languages', 'Popular programming languages for software development', 'programming-languages'),
   ('Web Servers', 'HTTP servers and reverse proxies for serving web applications', 'web-servers'),
   ('Message Queues', 'Systems for asynchronous communication and task processing', 'message-queues'),
   ('Monitoring & Analytics', 'Tools for application performance monitoring and analytics', 'monitoring-analytics'),
   ('Testing Frameworks', 'Libraries and tools for automated testing', 'testing-frameworks'),
   ('Version Control', 'Systems for tracking changes in source code', 'version-control'),
   ('Containerization', 'Technologies for application containerization and orchestration', 'containerization'),
   ('Authentication', 'Identity and access management solutions', 'authentication'),
   ('Search Engines', 'Full-text search and analytics engines', 'search-engines');

-- Insert technologies
INSERT INTO technology (name, short_description, description, slug, website_url, github_url, documentation_url, logo_url) VALUES
-- Frontend Frameworks
('React', 'A JavaScript library for building user interfaces', 'React is a free and open-source front-end JavaScript library for building user interfaces based on components. It is maintained by Meta and a community of individual developers and companies.', 'react', 'https://reactjs.org', 'https://github.com/facebook/react', 'https://reactjs.org/docs', 'https://raw.githubusercontent.com/facebook/react/main/fixtures/dom/public/react-logo-192.png'),

('Vue.js', 'The Progressive JavaScript Framework', 'Vue.js is an open-source model–view–viewmodel front end JavaScript framework for building user interfaces and single-page applications. It was created by Evan You and is maintained by him and the rest of the active core team members.', 'vuejs', 'https://vuejs.org', 'https://github.com/vuejs/vue', 'https://vuejs.org/guide/', 'https://vuejs.org/images/logo.png'),

('Angular', 'Platform for building mobile and desktop web applications', 'Angular is a TypeScript-based free and open-source web application framework led by the Angular Team at Google and by a community of individuals and corporations.', 'angular', 'https://angular.io', 'https://github.com/angular/angular', 'https://angular.io/docs', 'https://angular.io/assets/images/logos/angular/angular.png'),

('Svelte', 'Cybernetically enhanced web apps', 'Svelte is a free and open-source front end component framework or language created by Rich Harris and maintained by the Svelte core team members.', 'svelte', 'https://svelte.dev', 'https://github.com/sveltejs/svelte', 'https://svelte.dev/docs', 'https://svelte.dev/svelte-logo-horizontal.svg'),

-- Backend Frameworks
('Spring Boot', 'Create stand-alone, production-grade Spring applications', 'Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run". It takes an opinionated view of the Spring platform and third-party libraries.', 'spring-boot', 'https://spring.io/projects/spring-boot', 'https://github.com/spring-projects/spring-boot', 'https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/', 'https://spring.io/images/spring-logo-9146a4d3298760c2e7e49595184e1975.svg'),

('Express.js', 'Fast, unopinionated, minimalist web framework for Node.js', 'Express.js is a back end web application framework for building RESTful APIs with Node.js. It has been called the de facto standard server framework for Node.js.', 'expressjs', 'https://expressjs.com', 'https://github.com/expressjs/express', 'https://expressjs.com/en/guide/routing.html', 'https://expressjs.com/images/express-facebook-share.png'),

('Django', 'The web framework for perfectionists with deadlines', 'Django is a high-level Python web framework that encourages rapid development and clean, pragmatic design. Built by experienced developers, it takes care of much of the hassle of web development.', 'django', 'https://www.djangoproject.com', 'https://github.com/django/django', 'https://docs.djangoproject.com', 'https://static.djangoproject.com/img/logos/django-logo-positive.png'),

('Ruby on Rails', 'Web development that does not hurt', 'Ruby on Rails is a server-side web application framework written in Ruby under the MIT License. Rails is a model–view–controller framework, providing default structures for a database, a web service, and web pages.', 'rails', 'https://rubyonrails.org', 'https://github.com/rails/rails', 'https://guides.rubyonrails.org', 'https://rubyonrails.org/images/rails-logo.svg'),

('ASP.NET Core', 'Cross-platform .NET framework for building modern applications', 'ASP.NET Core is a cross-platform, high-performance, open-source framework for building modern, cloud-enabled, Internet-connected apps.', 'aspnet-core', 'https://dotnet.microsoft.com/apps/aspnet', 'https://github.com/dotnet/aspnetcore', 'https://docs.microsoft.com/en-us/aspnet/core/', 'https://dotnet.microsoft.com/static/images/redesign/downloads-dot-net.svg'),

-- Databases
('PostgreSQL', 'The world''s most advanced open source relational database', 'PostgreSQL is a powerful, open source object-relational database system with over 35 years of active development that has earned it a strong reputation for reliability, feature robustness, and performance.', 'postgresql', 'https://www.postgresql.org', 'https://github.com/postgres/postgres', 'https://www.postgresql.org/docs/', 'https://www.postgresql.org/media/img/about/press/elephant.png'),

('MongoDB', 'The database for modern applications', 'MongoDB is a source-available cross-platform document-oriented database program. Classified as a NoSQL database program, MongoDB uses JSON-like documents with optional schemas.', 'mongodb', 'https://www.mongodb.com', 'https://github.com/mongodb/mongo', 'https://docs.mongodb.com', 'https://www.mongodb.com/assets/images/global/favicon.ico'),

('MySQL', 'The world''s most popular open source database', 'MySQL is an open-source relational database management system. Its name is a combination of "My", the name of co-founder Michael Widenius''s daughter My, and "SQL", the acronym for Structured Query Language.', 'mysql', 'https://www.mysql.com', 'https://github.com/mysql/mysql-server', 'https://dev.mysql.com/doc/', 'https://www.mysql.com/common/logos/logo-mysql-170x115.png'),

('Redis', 'The open source, in-memory data store', 'Redis is an open source, in-memory data structure store, used as a database, cache, and message broker. Redis provides data structures such as strings, hashes, lists, sets, sorted sets with range queries.', 'redis', 'https://redis.io', 'https://github.com/redis/redis', 'https://redis.io/documentation', 'https://redis.io/images/redis-white.png'),

-- Cloud Platforms
('Amazon Web Services', 'Comprehensive and broadly adopted cloud platform', 'Amazon Web Services offers reliable, scalable, and inexpensive cloud computing services. Free to join, pay only for what you use.', 'aws', 'https://aws.amazon.com', NULL, 'https://docs.aws.amazon.com', 'https://a0.awsstatic.com/libra-css/images/logos/aws_logo_smile_1200x630.png'),

('Google Cloud Platform', 'Build and scale with Google Cloud''s infrastructure', 'Google Cloud Platform is a suite of cloud computing services that runs on the same infrastructure that Google uses internally for its end-user products, such as Google Search, Gmail, Google Drive, and YouTube.', 'gcp', 'https://cloud.google.com', NULL, 'https://cloud.google.com/docs', 'https://cloud.google.com/_static/cloud/images/social-icon-google-cloud-1200-630.png'),

('Microsoft Azure', 'Invent with purpose with Microsoft Azure', 'Microsoft Azure is a cloud computing service created by Microsoft for building, testing, deploying, and managing applications and services through Microsoft-managed data centers.', 'azure', 'https://azure.microsoft.com', NULL, 'https://docs.microsoft.com/en-us/azure/', 'https://azurecomcdn.azureedge.net/cvt-68b530dab7b01ac2b6c5089565ab5b1b042b1ab6f4e5b0efeb48a3d6cd35dcad/images/page/home/microsoft-azure-logo.png'),

-- DevOps Tools
('Docker', 'Accelerate how you build, share, and run applications', 'Docker is a set of platform as a service products that use OS-level virtualization to deliver software in packages called containers.', 'docker', 'https://www.docker.com', 'https://github.com/docker/docker-ce', 'https://docs.docker.com', 'https://www.docker.com/sites/default/files/d8/2019-07/vertical-logo-monochromatic.png'),

('Kubernetes', 'Production-Grade Container Orchestration', 'Kubernetes is an open-source container orchestration system for automating software deployment, scaling, and management. Google originally designed Kubernetes, but the Cloud Native Computing Foundation now maintains the project.', 'kubernetes', 'https://kubernetes.io', 'https://github.com/kubernetes/kubernetes', 'https://kubernetes.io/docs/', 'https://kubernetes.io/images/kubernetes-horizontal-color.png'),

('Jenkins', 'Build great things at any scale', 'Jenkins is a free and open source automation server. It helps automate the parts of software development related to building, testing, and deploying, facilitating continuous integration and continuous delivery.', 'jenkins', 'https://www.jenkins.io', 'https://github.com/jenkinsci/jenkins', 'https://www.jenkins.io/doc/', 'https://www.jenkins.io/images/logos/jenkins/jenkins.png'),

-- Mobile Development
('React Native', 'Create native apps for Android and iOS using React', 'React Native combines the best parts of native development with React, a best-in-class JavaScript library for building user interfaces.', 'react-native', 'https://reactnative.dev', 'https://github.com/facebook/react-native', 'https://reactnative.dev/docs/getting-started', 'https://reactnative.dev/img/header_logo.svg'),

('Flutter', 'Build apps for any screen', 'Flutter is Google''s UI toolkit for building beautiful, natively compiled applications for mobile, web, desktop, and embedded devices from a single codebase.', 'flutter', 'https://flutter.dev', 'https://github.com/flutter/flutter', 'https://docs.flutter.dev', 'https://storage.googleapis.com/cms-storage-bucket/4fd5520fe28ebf839174.svg'),

-- Programming Languages
('JavaScript', 'The programming language of the Web', 'JavaScript is a programming language that is one of the core technologies of the World Wide Web, alongside HTML and CSS. Over 97% of websites use JavaScript on the client side for web page behavior.', 'javascript', 'https://developer.mozilla.org/en-US/docs/Web/JavaScript', NULL, 'https://developer.mozilla.org/en-US/docs/Web/JavaScript', 'https://upload.wikimedia.org/wikipedia/commons/6/6a/JavaScript-logo.png'),

('Python', 'Programming language that lets you work quickly', 'Python is a high-level, general-purpose programming language. Its design philosophy emphasizes code readability with the use of significant indentation.', 'python', 'https://www.python.org', 'https://github.com/python/cpython', 'https://docs.python.org', 'https://www.python.org/static/community_logos/python-logo-master-v3-TM.png'),

('Java', 'Write once, run anywhere', 'Java is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible.', 'java', 'https://www.oracle.com/java/', 'https://github.com/openjdk/jdk', 'https://docs.oracle.com/en/java/', 'https://logos-world.net/wp-content/uploads/2022/07/Java-Logo.png'),

('TypeScript', 'JavaScript with syntax for types', 'TypeScript is a free and open source programming language developed and maintained by Microsoft. It is a strict syntactical superset of JavaScript and adds optional static type checking to the language.', 'typescript', 'https://www.typescriptlang.org', 'https://github.com/Microsoft/TypeScript', 'https://www.typescriptlang.org/docs/', 'https://www.typescriptlang.org/images/branding/logo-nocircle.svg'),

-- Version Control
('Git', 'Distributed version control system', 'Git is a distributed version-control system for tracking changes in any set of files, originally designed for coordinating work among programmers collaborating on source code during software development.', 'git', 'https://git-scm.com', 'https://github.com/git/git', 'https://git-scm.com/doc', 'https://git-scm.com/images/logos/downloads/Git-Logo-2Color.png'),

-- Authentication
('Auth0', 'Secure access for everyone', 'Auth0 is a flexible, drop-in solution to add authentication and authorization services to your applications. Your team and organization can avoid the cost, time, and risk that comes with building your own solution.', 'auth0', 'https://auth0.com', NULL, 'https://auth0.com/docs', 'https://cdn.auth0.com/styleguide/latest/lib/logos/img/logo.png'),

-- Search Engines
('Elasticsearch', 'Distributed, RESTful search and analytics engine', 'Elasticsearch is a distributed, free and open search and analytics engine for all types of data, including textual, numerical, geospatial, structured, and unstructured.', 'elasticsearch', 'https://www.elastic.co/elasticsearch/', 'https://github.com/elastic/elasticsearch', 'https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html', 'https://www.elastic.co/static-res/images/elastic-logo-200.png');

-- Insert technology-category relationships
INSERT INTO technology_category (technology_id, category_id) VALUES
-- Frontend Frameworks
(1, 1), -- React -> Frontend Frameworks
(2, 1), -- Vue.js -> Frontend Frameworks
(3, 1), -- Angular -> Frontend Frameworks
(4, 1), -- Svelte -> Frontend Frameworks

-- Backend Frameworks
(5, 2), -- Spring Boot -> Backend Frameworks
(6, 2), -- Express.js -> Backend Frameworks
(7, 2), -- Django -> Backend Frameworks
(8, 2), -- Ruby on Rails -> Backend Frameworks
(9, 2), -- ASP.NET Core -> Backend Frameworks

-- Databases
(10, 3), -- PostgreSQL -> Databases
(11, 3), -- MongoDB -> Databases
(12, 3), -- MySQL -> Databases
(13, 3), -- Redis -> Databases

-- Cloud Platforms
(14, 4), -- AWS -> Cloud Platforms
(15, 4), -- GCP -> Cloud Platforms
(16, 4), -- Azure -> Cloud Platforms

-- DevOps Tools
(17, 5), -- Docker -> DevOps Tools
(18, 5), -- Kubernetes -> DevOps Tools
(19, 5), -- Jenkins -> DevOps Tools

-- Containerization (Docker and Kubernetes also belong here)
(17, 13), -- Docker -> Containerization
(18, 13), -- Kubernetes -> Containerization

-- Mobile Development
(20, 6), -- React Native -> Mobile Development
(21, 6), -- Flutter -> Mobile Development

-- Programming Languages
(22, 7), -- JavaScript -> Programming Languages
(23, 7), -- Python -> Programming Languages
(24, 7), -- Java -> Programming Languages
(25, 7), -- TypeScript -> Programming Languages

-- Version Control
(26, 12), -- Git -> Version Control

-- Authentication
(27, 14), -- Auth0 -> Authentication

-- Search Engines
(28, 15), -- Elasticsearch -> Search Engines

-- Cross-category relationships (technologies that fit multiple categories)
-- JavaScript is also used in Frontend
(22, 1), -- JavaScript -> Frontend Frameworks (used with React, Vue, etc.)

-- TypeScript is also used in Frontend
(25, 1), -- TypeScript -> Frontend Frameworks (used with Angular, React, etc.)

-- Python is also used in Backend
(23, 2), -- Python -> Backend Frameworks (Django uses Python)

-- Java is also used in Backend
(24, 2), -- Java -> Backend Frameworks (Spring Boot uses Java)

-- Redis can also be used for Message Queues
(13, 9); -- Redis -> Message Queues