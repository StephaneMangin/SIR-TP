# Intégration Jpa, Servlet, AngularJS et GWT

## Branche Jpa (Persistance)

Cette branche est le tronc commun de toutes les intégrations.

## Branche Servlet

### Récupération de la branche :
  
    $ git branch Servlet
    $ git checkout Servlet
    $ git pull origin Servlet
    
### Configuration du projet

    $ mvn clean install
    $ mvn tomcat7:run
    
Ensuite aller à la page http://localhost:8080 pour acéder à la page principale.

## Branche Jersey (API REST)

Cette branche est le tronc commun des intégrations AngularJS et GWT.

## Branche AngularJS

### Récupération de la branche :

    $ git branch AngularJS
    $ git checkout AngularJS
    $ git pull origin AngularJS

### Configuration du projet

    $ cd src/main/webapp
    $ bower update
    $ cd ../../../
    $ mvn clean install
    $ mvn tomcat7:run
    
Ensuite aller à la page http://localhost:8080 pour acéder à la page AngularJS.
  
## Branche GWT

## NoSQL MongoDB

### Description
Ce TP a consisté en la création d'une application utilisant une base de données MongoDB. Il nous a permis de comprendre le fonctionnement des bases NoSQL, mais aussi leurs avantages et limites.

### Explications:
En premier lieu, on a installé la base de données `MongoDB` en suivant les différentes instructions données sur l'énoncé du TP. MongoDB est une base de données orientée document qui permet de stocker des données ayant une structure variable. Les données sont enregistrés dans des collections (équivalent table en BD relationnelle). Lorsque l'on veut ajouter une colonne à un document, on a pas besoin d'une mise à jour du schema car avec ce type BD on peut avoir des champs différents au sein d'une collection. Ensuite on a créé une application (projet `Maven`) utilisant `MORPHIA`, une API proche de `JPA` qui va permettre la persistance et la gestion des entités. Dans cette application, on a créé trois classes `Article`, `Person` et `Address`>> qui respectent le modèle suivant:
  - Un article est associé à plusieurs acheteurs
  - Un acheteur (`Person`) possède plusieurs adresses
Nous avons indiqué ces différentes associations avec des annotations `Morphia` qui sont un peu différentes de celles de JPA car non relationnelles. Par exemple pour le champs de la clé et les mapping au niveau des listes on utilise :
     @Id
     ObjectId id;
     @Reference
     List<Address> address = new ArrayList<Address>();
Et pour finir on a créé des instances de ces classes pour alimenter la base de données et tester mes requêtes.
REMARQUE : Avec Morphia il faut persister(save) toutes les instances contrairement à JPA lorsque qu'on avait par exemple person.add(home) en rendant persistante l'instance de Person, celle de Home l'est directement aussi.

### Avantages:
Performance dans le temps de restitution
Manipulation rapide des données malgré la charge et le volume important de ces derniers
Insertion des structures de données différentes dans les collections ou Modification des structures existantes de manière transparentes sans impact sur les autres entrées(plus de alter table)

### Limites:
Manque d'intégrité transactionnelle importante pour certaines fonctionnalités.

### Réponses au TP NoSQL REDIS

`Redis` est un type de base de données NoSQL qui permet de manipuler de simples données et des ensembles ordonnées et stocke ces données sous forme de clé/valeur.
#### Exemple 1
    public class App {
      public static void main( String[] args ) {
        Jedis jedis = new Jedis("localhost");
        jedis.set("foo", "bar");
          String value = jedis.get("foo");
          System.err.println(value);    
        }
    }
Dans ce exemple, on stocke une chaine de caractère <<bar>> avec pour clé <<foo>>. Et en faisant un get sur la clé, la valeur qui lui ai associée c'est-à-dire bar est retournée.Ca se comporte comme un hashmap.

#### Exemple 2
    public static void main(String[] args) {  
      Jedis jedis = new Jedis("localhost");
      System.out.println(jedis.get("counter"));
      jedis.incr("counter");
      System.out.println(jedis.get("counter"));
    }
Dans cet autre exemple, le premier <<System.out.println(jedis.get("counter"))>> retourne null vue qu'il y'a aucune valeur qui lui ai associée. le deuxième va retourner 1 car l'instruction <<jedis.incr("counter")>> definit directement le type (int ou long) du champ devant être associé à la clé <<counter>>, l'initialise à 0 puis l'incrémente ce qui donne la valeur 1.

#### Exemple 3
    public static void main(String[] args) throws InterruptedException {
      String cacheKey = "cachekey";
      Jedis jedis = new Jedis("localhost");
      
      // adding a new key
      jedis.set(cacheKey, "cached value");
      
      // setting the TTL in seconds
      jedis.expire(cacheKey, 15);
      
      // Getting the remaining ttl
      System.out.println("TTL:" + jedis.ttl(cacheKey));
      Thread.sleep(3000);
      System.out.println("TTL:" + jedis.ttl(cacheKey));
      
      // Getting the cache value
      System.out.println("Cached Value:" + jedis.get(cacheKey));
      
      // Wait for the TTL finishs
      Thread.sleep(15000);
      
      // trying to get the expired key
      System.out.println("Expired Key:" + jedis.get(cacheKey));
    }
Ici, l'instruction <<jedis.expire(cacheKey, 15)>> fixe la durée de vie de la donnée à 15s et après chaque 3000ms(3s), on affiche la valeur associé à clé <<Thread.sleep(3000)>> ce qui donne 12 (15 - 3). Et après les 15000ms = 15s la donnée est détruite vu que sa durée de vie était fixée à 15 ms ce qui renvoie donc null.

#### Exemple 4
    public static void main(String[] args) {
      String cacheKey = "languages";
      Jedis jedis = new Jedis("localhost");
      // Adding a set as value
      jedis.sadd(cacheKey, "Java");
      jedis.sadd(cacheKey, "C#");
      jedis.sadd(cacheKey, "Python"); // SADD
  
      // Getting all values in the set: SMEMBERS
      System.out.println("Languages: " + jedis.smembers(cacheKey));
      // Adding new values
      jedis.sadd(cacheKey, "Java");
      jedis.sadd(cacheKey, "Ruby");
      // Getting the values... it doesn't allow duplicates
      System.out.println("Languages: " + jedis.smembers(cacheKey));
    }
Dans ce dernier exemple, une liste de chaine de caractère est associée à la clé <<cacheKey>> et le syso affiche un ensemble (Set) dont les éléments sont de types chaines de caractère: C#,Java,Python,Ruby


#### Types de données stockés dans Redis

Redis est une grosse HashMap qui permet le stockage des données structurées telles que :
  - Les chaines de caractère
  - Les Listes
  - Les Hash
  - Les sets (ensembles)
  - Les sets trié (ensembles ordonnés)

#### Conclusion

L’utilisation de redis est très simple et la vitesse de lecture et d’écriture est vertigineuse (très rapide). Il n'ya pas un problème de concurrence d'accès aux données car il y'a atomicité des opérations.
Avec Redis on peut pas faire de requête sur les valeus des champs comme on le fait avec la clause where en MySQL.Pour avoir la valeur d'un champ il faut passer par sa clé.
