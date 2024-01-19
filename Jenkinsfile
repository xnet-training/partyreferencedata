pipeline {
  agent any

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
            customImage = docker.build("xnet/partyreferencedata:1.0.2")
            customImage.push()
          }
      }
    }
  }
}
