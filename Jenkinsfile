pipeline {
  agent any

  stages {
    stage('Check-Out') {
      steps {
        script {
          sh 'git clone https://github.com/xnet-training/partyreferencedata.git'
        }
      }
    }
    stage('Construir Imagen de Contenedor') {
      container('kaniko') {
        script {
          sh '''
            /kaniko/executor --dockerfile `pwd`/Dockerfile \
              --context `pwd` \
              --destination=xnet/partyreferencedata:${BUILD_NUMBER}
          '''
        }
      } 
    }
  }
}
