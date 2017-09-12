// http://eslint.org/docs/user-guide/configuring
module.exports = {
	root: true,
	parser: 'babel-eslint',
	parserOptions: {
		sourceType: 'module'
	},
	env: {
		browser: true,
		node: true,
		es6: true,
	},
	// https://github.com/standard/standard/blob/master/docs/RULES-en.md
	extends: 'eslint:recommended',
	// required to lint *.vue files
	plugins: ['html'],
	// check if imports actually resolve
	'settings': {
		'import/resolver': {
			'webpack': {
				'config': 'build/webpack.base.conf.js'
			}
		}
	},
	// add your custom rules here
	'rules': {
		'indent': 0,
		'no-tabs': 0,
		'no-console': 0,
		'accessor-pairs': 2,
		'arrow-spacing': [2, {
			'before': true,
			'after': true
		}],
		'block-spacing': [2, 'always'],
		'brace-style': [2, '1tbs', {
			'allowSingleLine': true
		}],
		'camelcase': [0, {
			'properties': 'always'
		}],
		'comma-dangle': [2, 'never'],
		'comma-spacing': [2, {
			'before': false,
			'after': true
		}],
		'comma-style': [2, 'last'],
		'constructor-super': 2,
		'curly': [2, 'multi-line'],
		'dot-location': [2, 'property'],
		'eol-last': 2,
		'generator-star-spacing': [2, {
			'before': true,
			'after': true
		}],
		'handle-callback-err': [2, '^(err|error)$'],
		'key-spacing': [2, {
			'beforeColon': false,
			'afterColon': true
		}],
		'keyword-spacing': [2, {
			'before': true,
			'after': true
		}],
		'new-cap': [2, {
			'newIsCap': true,
			'capIsNew': false
		}],
		'new-parens': 2,
		'operator-linebreak': [2, 'after', {
			'overrides': {
				'?': 'before',
				':': 'before'
			}
		}],
		'padded-blocks': [2, 'never'],
		'semi': [0, 'never'],
		'semi-spacing': [2, {
			'before': false,
			'after': true
		}],
		'space-before-blocks': [2, 'always'],
		'space-before-function-paren': [2, 'never'],
		'space-in-parens': [2, 'never'],
		'space-infix-ops': 2,
		'space-unary-ops': [2, {
			'words': true,
			'nonwords': false
		}],
		'spaced-comment': [2, 'always', {
			'markers': ['global', 'globals', 'eslint', 'eslint-disable', '*package', '!', ',']
		}],
		'template-curly-spacing': [2, 'never'],
		'use-isnan': 2,
		'valid-typeof': 2,
		'yield-star-spacing': [2, 'both'],
		'yoda': [2, 'never'],
		'prefer-const': 2,
		'no-debugger': process.env.NODE_ENV === 'production' ? 2 : 0,
		'object-curly-spacing': [2, 'always', {
			objectsInObjects: false
		}],
		'array-bracket-spacing': [2, 'never']
	}
}
