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
      steps {
        script {
          sh 'echo "Generando Imagen"'
          sh 'docker build . -t partyreferencedata:1.0.2'
        }
      }
    }
  }
}
