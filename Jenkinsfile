pipeline {
  agent any
  //agent { docker { image 'maven:3.8.5-jdk-11' } }

  stages {
    stage('Check-Out') {
      steps {
        //script {
        //  sh 'git clone https://github.com/xnet-training/partyreferencedata.git'
        //}
        checkout(
          [
            $class: 'GitSCM', 
            branches: [[name: '*/main']], 
            doGenerateSubmoduleConfigurations: false, 
            extensions: [], 
            submoduleCfg: [], 
            userRemoteConfigs: [[credentialsId: 'ianache', url: 'https://github.com/xnet-training/partyreferencedata.git']]])
      }
    }
    stage('Construir Imagen de Contenedor') {
      steps {
          script {
            //customImage = docker.build("xnet/partyreferencedata:1.0.2")
            //customImage.push()
          
          
            sh 'echo "docker build . -t partyreferencedata:1.0.2"'
          //  // sh 'docker build . -t partyreferencedata:1.0.2'
          }
      }
    }
    stage('Desplegar microservicio') {
      steps {
        script {
          sshPut remote: [name: 'docker-compose.yaml', host: '172.17.8.28', user: 'vagrant', password: 'vagrant', allowAnyHosts: true ], 
                 from: './dev-environment/docker-compose.yaml',
                 to: '/opt/devops/partyreferencedata/'
          //sh '''
          //   echo "Desplegando Microservicio"
             
          //   sshpass -p vagrant scp ./dev-environment/docker-compose.yaml vagrant@172.17.8.28:/opt/devops/partyreferencedata/
          //   sshpass -p vagrant scp ./dev-environment/.env vagrant@172.17.8.28:/opt/devops/partyreferencedata/
          //   sshpass -p vagrant ssh vagrant@172.17.8.28 sh 'cd /opt/devops/partyreferencedata && docker compose -f docker-compose.yaml up -d'
          //'''
        }
      }
    }
    stage('Ejecutar Suite de Pruebas Funcionales') {
      agent { docker { image 'xnet/postman_newman:alpine-1.0.0' }}
      steps {
        script {
          sh '''
             cd test/functional/postman
             newman run Test_wd.postman_collection --folder='CP1.3 Registro de persona menor de edad' --iteration-data CP1.3_Datapool.csv --folder='CP1.1 Registro de Persona Nueva' --iteration-data CP1.1_Datapool.csv --verbose
          '''
        }
      }
      post {
        success {
           echo "Send Ok"
        }
        failure  {
           echo "Send Alert"
        }
      }
    }
  }
}
